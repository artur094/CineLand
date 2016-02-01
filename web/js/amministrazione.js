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
    
});