  $(document).ready(function() {
    $('select').material_select();


 
    
    $('.check_ridotti').on('click',function(){
        if(document.getElementById("ridotti").checked){
            $('.riduzioni').addClass("show");
        }else{
            $('.riduzioni').removeClass("show");
        }
    });
    $('#now').text(Date());
    $('.img_posto').on("click", function(){
        if(!$(this).hasClass("prenotato")){
            if($(this).hasClass("selezionato")){
                $(this).removeClass("selezionato");
                $(this).attr("src","img/chair20.png");
                countPosti--;
                
                if((countPosti-studenti-militari-anziani-disabili)<0)
                {
                    studenti=0;
                    militari=0;
                    anziani=0;
                    disabili=0;
                }
                
                creaDrop();
            }else{
                if(countPosti<maxCount)
                {
                    $(this).addClass("selezionato");
                    $(this).attr("src","img/chairGreen.png");
                    countPosti++;
                    
                    $('select').material_select('destroy');
                    $('.selStudenti').append("<option value=\""+(countPosti-militari-anziani-disabili)+"\">"+(countPosti-militari-anziani-disabili)+"</option>");
                    $('.selMilitari').append("<option value=\""+(countPosti-studenti-anziani-disabili)+"\">"+(countPosti-studenti-anziani-disabili)+"</option>");
                    $('.selAnziani').append("<option value=\""+(countPosti-studenti-militari-disabili)+"\">"+(countPosti-studenti-militari-disabili)+"</option>");
                    $('.selDisabili').append("<option value=\""+(countPosti-studenti-militari-anziani)+"\">"+(countPosti-studenti-militari-anziani)+"</option>");
                }
            }
            $('.countPostiSel').text("Posti selezionati: "+countPosti);
            $('select').material_select();
            creaEventi();
        }
});
  });
 