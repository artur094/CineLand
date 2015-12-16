$( document ).ready(function() {

    var slider = $('.slider');
    var indexShow = 0;
    var timerScroll;
    var images = $('.slider-img');
    var length = images.length;
    timeScroll();
    
    function timeScroll(){
        timerScroll = setInterval(rightScroll,7000);
    }
            
    function rightScroll(){
        //var first = $('.slider:first-child');
        $('.slider-img').last().clone().prependTo('.slider');
        $('.slider-img').last().fadeOut( 3000, function() {
            $('.slider-img').last().remove();
        });
    }
});