$(document).ready(function() {
    var price = 8, //price
        ridotto1 = 5,
        ridotto2 = 6,
        countPosti = 0,//contatore posti selezionati
        maxCount = 15,//max numero posti selezionabili
        studenti= 0,//contatore ridotti studente
        militari=0,//contatore ridotti militari
        anziani=0,//contatore ridotti anziani
        disabili = 0,//contatore ridotti disabili     
        $cart = $('#selected-seats'), //Sitting Area
        $counter = $('#counter'), //Votes
        $total = $('#total'),
        s = "",//wrapper per costruire la stringa di posti
        id_spett = parseInt($('#id_spett').data('id'));
        
    $('select').material_select();
    $('.check_ridotti').on('click',function(){
        if(document.getElementById("ridotti").checked){
            $('.riduzioni').addClass("show");
        }else{
            $('.riduzioni').removeClass("show");
        }
    });
    $('#now').text(Date());   
    $.ajax({
        type : 'POST',
        url : 'Controller',           
        data: {
            op : "vettore_posti_sala",
            id_spett: id_spett
        },
        success:function (data) {
            creaMappa(data);
            aggiorna(id_spett);
        }
    });
    $('.seatCharts-space').each(function(){
        $(this).append("<div class=\"stair\"><div class=\"stair_up\"></div>&nbsp;</div>");
    });    
    function creaMappa(data){
        sc = $('#seat-map').seatCharts({
            map: data,
            naming : {
                    top : false,
                    left:false,
                    getLabel : function (character, row, column) {
                            return "";
                    }
            },
            legend : { //Definition legend
                    node : $('#legend'),
                    items : [
                            [ 'a', 'selected-legend',   'Option' ],
                            [ 'a', 'unavailable', 'Sold']
                    ]
            },
            click: function () { //Click event
                    if (this.status() == 'available') { //optional seat
                        if(countPosti<maxCount){
                            var r = (this.settings.id.toString().split('_')[0]);
                            var c = (this.settings.id.toString().split('_')[1]);
                            $('<li class="selected">R'+r+' P'+c+'</li>')
                                .attr('id', 'cart-item-'+this.settings.id)
                                .data('r', r)
                                .data('c', c)
                                .appendTo($cart);
                            $counter.text(sc.find('selected').length+1);
                            $total.text(recalculateTotal(sc)+price);
                            countPosti++;
                            $('select').material_select('destroy');
                            $('.selStudenti').append("<option value=\""+(countPosti-militari-anziani-disabili)+"\">"+(countPosti-militari-anziani-disabili)+"</option>");
                            $('.selMilitari').append("<option value=\""+(countPosti-studenti-anziani-disabili)+"\">"+(countPosti-studenti-anziani-disabili)+"</option>");
                            $('.selAnziani').append("<option value=\""+(countPosti-studenti-militari-disabili)+"\">"+(countPosti-studenti-militari-disabili)+"</option>");
                            $('.selDisabili').append("<option value=\""+(countPosti-studenti-militari-anziani)+"\">"+(countPosti-studenti-militari-anziani)+"</option>");
                            $('select').material_select();
                            creaEventi();
                            return 'selected';
                        }
                            return 'available';

                    } else if (this.status() == 'selected') { //Checked
                                    //Update Number
                                    $counter.text(sc.find('selected').length-1);
                                    //update totalnum
                                    $total.text(recalculateTotal(sc)-price);
                                    //Delete reservation
                                    $('#cart-item-'+this.settings.id).remove();
                                    //optional
                                    countPosti--;
                                    if((countPosti-studenti-militari-anziani-disabili)<0){
                                        studenti=0;
                                        militari=0;
                                        anziani=0;
                                        disabili=0;
                                    }
                                    creaDrop();
                                    $('select').material_select();
                                    creaEventi();
                                    return 'available';
                    } else if (this.status() == 'unavailable') { //sold
                            return 'unavailable';
                    } else {
                            return this.style();
                    }
            },
            focus: function(){return this.style();} 
        });
    }
    
    function aggiorna(id_spett){
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_occupati",
                id_spett: id_spett
            },
            success:function (data) {
                sc.find('unavaible').status('available');
                sc.get(data).status('unavailable');
            }
        });
    }

    function recalculateTotal(sc) {
            var total = 0;
            sc.find('selected').each(function () {
                    total += price;
            });
            var riduzione_militari = (price-ridotto2)*militari;
            var riduzione_studenti = (price-ridotto1)*studenti;
            var riduzione_anziani = (price-ridotto1)*anziani;
            var riduzione_disabili = (price-ridotto1)*disabili;
            var totale = total-riduzione_militari-riduzione_studenti-riduzione_disabili-riduzione_anziani;
            return totale;
    }//sum total money

    function creaEventi(){
        $('.selStudenti .select-dropdown li span').on("click",function(){
            studenti = $(this).text();
            $total.text(recalculateTotal(sc));
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selMilitari .select-dropdown li span').on("click",function(){
            militari = $(this).text();
            $total.text(recalculateTotal(sc));
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selAnziani .select-dropdown li span').on("click",function(){
            anziani = $(this).text();
            $total.text(recalculateTotal(sc));
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selDisabili .select-dropdown li span').on("click",function(){
            disabili = $(this).text();
            $total.text(recalculateTotal(sc));
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
    }

    function creaDrop(){
        $('select').empty();
        $('select').material_select('destroy');
        for(var i = 0; i <= countPosti-militari-anziani-disabili; i++)
        {
            //$('select').material_select('destroy');
            //alert(i);
            if(i==studenti)
            {
               $('.selStudenti').append("<option value=\""+i+"\" selected>"+i+"</option>"); 
            }
            else
                $('.selStudenti').append("<option value=\""+i+"\">"+i+"</option>");

            //$('select').material_select();
        }

        for(var i = 0; i <= countPosti-studenti-anziani-disabili; i++)
        {
            if(i==militari)
            {
               $('.selMilitari').append("<option value=\""+i+"\" selected>"+i+"</option>"); 
            }
            else
                $('.selMilitari').append("<option value=\""+i+"\">"+i+"</option>");
        }

        for(var i = 0; i <= countPosti-studenti-militari-disabili; i++)
        {
            if(i==anziani)
            {
               $('.selAnziani').append("<option value=\""+i+"\" selected>"+i+"</option>"); 
            }
            else
                $('.selAnziani').append("<option value=\""+i+"\">"+i+"</option>");
        }


        for(var i = 0; i <= countPosti-studenti-militari-anziani; i++)
        {
            if(i==disabili)
            {
               $('.selDisabili').append("<option value=\""+i+"\" selected>"+i+"</option>"); 
            }
            else
                $('.selDisabili').append("<option value=\""+i+"\">"+i+"</option>");
        }

        //$('select').material_select('destroy');
        creaEventi();
    }
    $('#btn_paga').click(function(){
        var st = studenti;
        var mi =militari;
        var an = anziani;
        var di = disabili;
        s="";
        $('li.selected').each(function(i){
            s=s+$(this).data('r')+","+$(this).data('c')+",";
            if(st!=0){
                s = s+"s ";
                st--;
            }else if(mi>0){
                s = s+"m "
                mi--;
            }else if(an>0){
                s = s+"a ";
                an--;
            }else if(di>0){
                s=s+"d "
                di--;
            }else
                s=s+"n ";
            });
            if(s==""){
                $('#btn_conferma').addClass('disabled');
            }else{
                $('#btn_conferma').removeClass('disabled')
            }
            
        $('.msgPagamento').hide();
        $('.msgErrore').hide();
        $('.waiting').hide();
        $('#payment-method').show();
    });
    $('#btn_conferma').click(function(){
        
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "prenota",
                spettacolo: id_spett,
                posti: s,
                card: $('.creditcards input.selected').attr('id'),
                titolare: $('#first_name').val(),
                numero_carta: $('#number_card').val(),
                cvv: $('#cvv').val(),
                mese: $('#scadenzaMese').val(),
                anno: $('#scadenzaAnno').val()
            },
            beforeSend:function(){
                $('#payment-method').fadeOut(function(){
                    $('.waiting').fadeIn();
                });
            },
            success:function (data) {
                aggiorna(id_spett);
                console.log(data);
                if(data=="1"){
                    $('#selected-seats').empty();
                    $('.waiting').fadeOut(function(){
                        $('.msgPagamento').text("Prenotazione confermata");
                        $('.msgPagamento').fadeIn();
                    });
                }else{
                    $('.waiting').fadeOut(function(){
                        $('.msgErrore').text("Prenotazione rifiutata! Posti non disponibili oppure carta non valida");
                        $('.msgErrore').fadeIn();
                    });
                }
            },
            error:function(){
                aggiorna(id_spett);
                $('#selected-seats').empty();
                $('.msgErrore').text("Prenotazione rifiutata! Posti non disponibili oppure carta non valida");
                $('.waiting').hide(function(){
                    
                $('.msgErrore').delay(500).fadeIn();
                });
            }
        });
        s="";
    });

});
 