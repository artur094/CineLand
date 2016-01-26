$(document).ready(function() {
    var price = 8; //price
    var ridotto1 = 5;
    var ridotto2 = 6;
    var countPosti = 0;
    var maxCount = 15;
    var studenti= 0;
    var militari=0;
    var anziani=0;
    var disabili = 0;     
    var $cart = $('#selected-seats'), //Sitting Area
        $counter = $('#counter'), //Votes
        $total = $('#total'); //Total money
        
    $('select').material_select();
    $('.check_ridotti').on('click',function(){
        if(document.getElementById("ridotti").checked){
            $('.riduzioni').addClass("show");
        }else{
            $('.riduzioni').removeClass("show");
        }
    });
    $('#now').text(Date());
    var id_spett = $('#id_spett').data('id');
    $.ajax({
        type : 'POST',
        url : 'Controller',           
        data: {
            op : "vettore_posti_occupati",
            id_spett: id_spett
        },
        success:function (data) {
            creaMappa(data);
        }
    });
    
    

    
    function creaMappa(data){
        var sc = $('#seat-map').seatCharts({
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
                                [ 'a', 'unavailable', 'Sold'],
                        ]
                },
                click: function () { //Click event
                        if (this.status() == 'available') { //optional seat
                            if(countPosti<maxCount){
                                $('<li>R'+(this.settings.id.toString().split('_')[0])+' P'+(this.settings.id.toString().split('_')[1])+'</li>')
                                        .attr('id', 'cart-item-'+this.settings.id)
                                        .data('seatId', this.settings.id)
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
    }        //sum total money

        $('.seatCharts-space').each(function(){
            $(this).append("<div class=\"stair\"><div class=\"stair_up\"></div>&nbsp;</div>");

        });

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
    }

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
    
});
 