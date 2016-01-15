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
    
    
});
 