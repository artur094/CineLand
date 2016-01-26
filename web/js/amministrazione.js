/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var sc= [];
  
    $('.item_spett').click(function(){
        var index = $(this).index();
        var id_spett = $('.item_spett').eq(index).data('id_spett');
        if(sc[index]=== undefined){
            creaMappa(index,id_spett);
        }else
            aggiorna(index,id_spett);
        $('#message1').hide();
        $('.item_spett').removeClass('activeitem');
        $('.item_spett').eq($(this).index()).addClass('activeitem');
        $('.seatCharts-container').hide();
        $("#seat-map"+index).show();
    });
    
    function creaMappa(index, id_spett){
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
    
});