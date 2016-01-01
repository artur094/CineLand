/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var price = 8; //price
var price_s_a_d = 5;
var price_m = 6;
var countPosti = 0;
var maxCount = 15;
var studenti= 0;
var militari=0;
var anziani=0;
var disabili = 0;
var sala1 = [  //Seating chart
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaaa'
            ];
var sala2 = [  //Seating chart
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_',
        '_aaaaaaaaaaaaaaaaa_'
            ];
$(document).ready(function() {

    var $cart = $('#selected-seats'), //Sitting Area
    $counter = $('#counter'), //Votes
    $total = $('#total'); //Total money

    var sc = $('#seat-map').seatCharts({
            map: sala2,
            naming : {
                    top : false,
                    getLabel : function (character, row, column) {
                            return column;
                    }
            },
            legend : { //Definition legend
                    node : $('#legend'),
                    items : [
                            [ 'a', 'available',   'Option' ],
                            [ 'a', 'unavailable', 'Sold']
                            
                    ]					
            },
            click: function () { //Click event
                    if (this.status() == 'available') { //optional seat
                        if(countPosti<maxCount){
                            $('<li>R'+(this.settings.row+1)+' S'+this.settings.label+'</li>')
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
            }
    });
    //sold seat
    sc.get(['1_2', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');
		
});
//sum total money
function recalculateTotal(sc) {
	var total = 0;
	sc.find('selected').each(function () {
		total += price;
	});
			
	return total;
}

    function creaEventi(){
        $('.selStudenti .select-dropdown li span').on("click",function(){
            studenti = $(this).text();
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selMilitari .select-dropdown li span').on("click",function(){
            militari = $(this).text();
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selAnziani .select-dropdown li span').on("click",function(){
            anziani = $(this).text();
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
        $('.selDisabili .select-dropdown li span').on("click",function(){
            disabili = $(this).text();
            creaDrop();
            $('select').material_select();
            creaEventi();
        });
    }
    
    function creaDrop(){
        $('select').empty();
        $('select').material_select('destroy');
        //$('.selStudenti').append("<option value=\""+countPosti+"\">"+countPosti+"</option>");
       
        
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


