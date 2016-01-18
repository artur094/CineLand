/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var C = [  //Seating chart
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

    var sc= [];
    $('.item_spett').each(function(index){
        $('.demo').append('<div id="seat-map'+index+'"></div>');
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "matrice_posti_sala",
                id_sala: $('.item_spett').eq(index).data('sala')
            },
            success:function (data) {
                console.log(data[0]);
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
                sc[index].get(['1_2', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');
            }
        });   
    });
    $('.seatCharts-space').each(function(){
            $(this).append("<div class=\"stair\"><div class=\"stair_up\"></div>&nbsp;</div>");
        });
    $("#seat-map0").show();
    $('.item_spett').eq(0).addClass('activeitem');
    $('.item_spett').click(function(){
        $('.item_spett').removeClass('activeitem');
        $(this).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map"+$(this).index()).show();
    });
    
   

    

});