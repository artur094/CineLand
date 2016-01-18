/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {


    var sc= [];
    $('.item_spett').each(function(index){
        $('.demo').append('<div id="seat-map'+index+'"></div>');
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_sala",
                id_sala: $('.item_spett').eq(index).data('sala')
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
            }
          
        });
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_occupati",
                id_sala: $('.item_spett').eq(index).data('sala')
            },
            success:function (data) {
                console.log(data);
                sc[index].get(data).status('unavailable');
            }
        });
    });
//    $('.seatCharts-space').each(function(){
//            $(this).append("<div class=\"stair\"><div class=\"stair_up\"></div>&nbsp;</div>");
//    });
    
    $('.item_spett').click(seleziona_spettacolo(1));
    
    
    function seleziona_spettacolo(pos){
        console.log(pos);
        $('.item_spett').removeClass('activeitem');
        $('.item_spett').eq(pos).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map"+pos).show();
}
});


