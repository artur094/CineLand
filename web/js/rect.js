$( document ).ready(function() { 
    $('.rect-video').mouseenter(function(){
        var vid = $(this).find('video').get(0);
        vid.play();
    }).mouseleave(function(){
        var vid = $(this).find('video').get(0);
        vid.pause();
        //vid.currentTime = 0; 
        vid.load();
    });
});