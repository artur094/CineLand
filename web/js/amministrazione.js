/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var sc= [];
    var sp= [];
  
    $('.item_spett').click(function(){
        var index = $(this).index();
        var id_spett = $('.item_spett').eq(index).data('id_spett');
        if(sc[index]=== undefined){
            creaMappaSpettacoli(index,id_spett);
        }else
            aggiorna(index,id_spett);
        $('#message1').hide();
        $('.item_spett').removeClass('activeitem');
        $('.item_spett').eq($(this).index()).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map"+index).show();
    });
    
    $('.item_sala').click(function(){
        var index = $(this).index();
        var id_sala = parseInt($('.item_sala').eq(index).data('sala'));
        console.log(typeof id_sala);
        if(sp[index]=== undefined){
            creaMappaSala(index,id_sala);
        }else
            aggiornaSala(index,id_sala);
        $('#message2').hide();
        $('.item_sala').removeClass('activeitem');
        $('.item_sala').eq($(this).index()).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map-sala"+index).show();
    });
    
    function creaMappaSpettacoli(index, id_spett){
        $('.demo').append('<div id="seat-map'+index+'"></div>');
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_sala",
                id_spett: id_spett
            },
            success:function (data) {
                sc[index] = $('#seat-map'+index).seatCharts({
                    map: data,
                    naming : {
                            top : false,
                            left:false,
                            getLabel : function (character, row, column) {
                                    return "";
                            }
                    },
                    click: function(){ //Click event
                                    return this.style();
                    },
                    focus: function(){ //Click event
                                    return this.style();
                    }
                });
                aggiorna(index,id_spett);
                $('.seatCharts-container').hide();
                $("#seat-map"+index).show();
            }
        });
    }
    function creaMappaSala(index, id_sala){
        $('.mappa_piùprenotati').append('<div id="seat-map-sala'+index+'"></div>');
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "admin_sala",
                id_sala: id_sala
            },
            success:function (data) {
                sp[index] = $('#seat-map-sala'+index).seatCharts({
                    map: data,
                    naming : {
                            top : false,
                            left:false,
                            getLabel : function (character, row, column) {
                                    return "";
                            }
                    },
                    click: function(){ //Click event
                                    return this.style();
                    },
                    focus: function(){ //Click event
                                    return this.style();
                    }
                });
                aggiornaSala(index,id_sala);
                $('.seatCharts-container').hide();
                $("#seat-map-sala"+index).show();
            }
        });
    }
    function aggiorna(index, id_spett){
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_occupati",
                id_spett: id_spett
            },
            success:function (data) {
                console.log(data);
                sc[index].find('unavaible').status('avaible');
                sc[index].get(data).status('unavailable');
            }
        });
    }
    function aggiornaSala(index, id_sala){
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "admin_postiprenotati",
                id_sala: id_sala
            },
            success:function (data) {
                console.log(data);
                sp[index].find('unavaible').status('avaible');
                sp[index].get(data).status('unavailable');
            }
        });
    }
    
    
    $('#btn_find').click(function(){
        var email = $('#cerca_user').val();
    
        //alert(email);
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "admin_prenotazioni",
                email: email
            },
            success:function (result) {
                console.log("result: "+result);
                if(result){
                    $('.container_acquisti').empty();
                    var dati = "";
                    dati+="<ul class=\"collapsible\" data-collapsible=\"expandable\">";
                    var lastSpett=-1;
                    var first=true;
                    for(var i=0;i<result.length;i++)
                    {
                        if(result[i].id_spettacolo!==lastSpett)
                        {
                            if(!first)
                                dati+="</div></li>";
                            else
                                first=false;
                            var time = new Date(result[i].data_ora_spettacolo);
                            dati+="<li><div class=\"collapsible-header\"><i class=\"material-icons\">receipt</i><b>"+result[i].titolo_film+"</b> <span class=\"material-icons\">schedule</span>"+time.getHours()+":"+time.getMinutes()+" - "+time.getDate()+"/"+(time.getMonth()+1)+"/"+time.getFullYear()+" <span><i class=\"material-icons right removeSpett\" data-idspett=\""+result[i].id_spettacolo+"\">close</i></span></div><div class=\"collapsible-body\">";
                            lastSpett = result[i].id_spettacolo;
                        }
                        dati+="<p><b>Sala</b>"+result[i].nome_sala+" Fila "+result[i].riga_posto+" Posto "+result[i].colonna_posto+" Costo "+result[i].prezzo+"&euro; <span><i class=\"material-icons right removePosto tiny\" data-idspett=\""+result[i].id_spettacolo+"\"  data-riga=\""+result[i].riga_posto+"\"  data-colonna=\""+result[i].colonna_posto+"\">close</i></span></p>";
                    }
                    dati+="</div></li></ul>";
                    
                    $('.container_acquisti').append(dati);
                    
                    $('.removeSpett').on('click',function(){
                        var posti = $(this).parent().parent().siblings().children('p').children('span').children('.removePosto');
                        //alert($(posti[0]).data('idspett'));
                        posti.each(function(index){
                            $.ajax({
                            type : 'POST',
                            url : 'Controller',           
                            data: {
                                op : "annulla_prenotazione",
                                id_spett: $(posti[index]).data('idspett'),
                                riga:$(posti[index]).data('riga'),
                                colonna:$(posti[index]).data('colonna')
                                }
                            });
                        });
                        
                        $(this).closest('li').remove();
                        
                        if($('.container_acquisti ul').children().length===0)
                            $('.container_acquisti ul').remove();
                    });
                    
                    $('.removePosto').on('click',function(){
                        $.ajax({
                        type : 'POST',
                        url : 'Controller',           
                        data: {
                            op : "annulla_prenotazione",
                            id_spett: $(this).data('idspett'),
                            riga:$(this).data('riga'),
                            colonna:$(this).data('colonna')
                            }
                        });
                        if($(this).parent().parent().siblings().length>=1)
                            $(this).closest('p').remove();
                        else
                            $(this).closest('li').remove();
                        
                        if($('.container_acquisti ul').children().length===0)
                            $('.container_acquisti ul').remove();
                            
                    });
                    
                    $('.collapsible').collapsible({
                        accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
                      });
                }
                else
                    alert("non vedo data");
            },
            error:function(){console.log("errore");}
        });
    });
});