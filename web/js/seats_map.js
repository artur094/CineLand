/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var price = 8; //price
var ridotto1 = 5;
var ridotto2 = 6;
var countPosti = 0;
var maxCount = 15;
var studenti= 0;
var militari=0;
var anziani=0;
var disabili = 0;
var sala1 = [  //Seating chart
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa',
        'aaaaaaaaa_aaaaaaaaa'
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
var sala3 = [
    '__aa___aa__',
    '_aaaa_aaaa_',
    '_aaaaaaaaaa_',
    '_aaaaaaaaaa_',
    '__aaaaaaaa__',
    '___aaaaaa___',
    '____aaaa____',
    '_____aa_____',
    '_____a_____'
];
var sala4 = [
    '________aaaaaa________',
    '_______a___a__a_______',
    '______a____a___a______',
    '______a________a______',
    '______a________a______',
    '______a________a______',
    '_______aaaaaaaa_______',
    '_______a______a_______',
    '_______a______a_______',
    '_______a______a_______',
    '_______a______a_______',
    '_______a______a_______',
    '_______a______a_______',
    '_______a______a_______',
    '__aaa__a______a__aaa__',
    '_a___a_a______a_a___a_',
    'a_____aa______aa_____a',
    'a_____aa______aa_____a'
];
$(document).ready(function() {

    var $cart = $('#selected-seats'), //Sitting Area
    $counter = $('#counter'), //Votes
    $total = $('#total'); //Total money

    var sc = $('#seat-map').seatCharts({
            map: sala4,
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
    //sold seat
    sc.get(['1_2', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');
    
    //sum total money
    
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

    
    

