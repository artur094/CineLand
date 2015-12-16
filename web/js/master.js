$( document ).ready(function(){
    
    // Initialize collapse button
    $(".button-collapse").sideNav();
    $('.modal-trigger').leanModal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        in_duration: 300, // Transition in duration
        out_duration: 200 // Transition out duration
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
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
});