$( document ).ready(function(){
    
    // Initialize collapse button
    $(".button-collapse").sideNav();
    
    $('.modal-trigger').leanModal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        in_duration: 300, // Transition in duration
        out_duration: 200 // Transition out duration
    });
    
    $("#btn_logout").click(function(){
        $.post("Controller",
        {
            op: "logout"
        },
        function(data, status){
            $("#login").removeClass("off");
            $("#logout").addClass("off");
        });
    });

    $("#btn_login").click(function(){
        var email = document.getElementById("email").value;
        var pwd = document.getElementById("password").value;
        $.post("Controller",
        {
            op: "login",
            email: email,
            pwd: pwd
        },
        function(data, status){
            if(data==900){
                
            }else if(data==910){
                $("#login").addClass("off");
                $("#logout").removeClass("off");
                $('#form').closeModal();
                $('.dropdown-button').dropdown({
                    inDuration: 300,
                    outDuration: 225,
                    constrain_width: false, // Does not change width of dropdown to that of the activator
                    gutter: 0, // Spacing from edge
                    belowOrigin: false, // Displays dropdown below the button
                    alignment: 'left' // Displays dropdown with edge aligned to the left of button
                });
            }
        });
    });
    
    
});

        
