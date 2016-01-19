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
                id_spett: $('.item_spett').eq(index).data('id_spett')
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
        
    });
//    $('.seatCharts-space').each(function(){
//            $(this).append("<div class=\"stair\"><div class=\"stair_up\"></div>&nbsp;</div>");
//    });
    
  
    
    $('.item_spett').click(function(){
        var index = $(this).index();
        $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "vettore_posti_occupati",
                id_spett: $(this).eq(index).data('id_spett')
            },
            success:function (data) {
                console.log(data);
                sc[index].get(data).status('unavailable');
            }
        });
        $('#message1').hide();
        $('.item_spett').removeClass('activeitem');
        $('.item_spett').eq($(this).index()).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map"+index).show();
        
    });
    
});


