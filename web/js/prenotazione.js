$('select').material_select();
  $(document).ready(function() {
    $('select').material_select();
    var countPosti = 0;
    var maxCount = 10;
    var studenti= 0;
    var militari=0;
    var anziani=0;
    var disabili = 0;
    
    function creaEventi()
    {
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
    
    function creaDrop()
    {
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
        $('.countPostiSel').text("Posti selezionati: "+countPosti + " Posti rimanenti: "+(countPosti-studenti-militari-anziani-disabili));
        creaEventi();
    }
    
    $('.img_posto').on("click", function(){
        if(!$(this).hasClass("prenotato"))
        {
            if($(this).hasClass("selezionato"))
            {
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
            }
            else
            {
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
            $('.countPostiSel').text("Posti selezionati: "+countPosti + " Posti rimanenti: "+(countPosti-studenti-militari-anziani-disabili));
            $('select').material_select();
            creaEventi();
        }
    });
    
});