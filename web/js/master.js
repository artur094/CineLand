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
        $.ajax({
        type : 'POST',
        url : 'Controller',           
        data: {
            op : "logout",
        },
        success:function (data) {
            window.location.replace("index.jsp");
        }
        });
    });

    $("#btn_login").click(function(){
        var email = document.getElementById("email").value;
        var pwd = document.getElementById("password").value;
        if(email==="" || pwd === ""){
            var $toastContent = $('<span>Email e password vuoti</span>');
            Materialize.toast($toastContent, 3000);
        }else{
            $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "login",
                email: email,
                pwd: pwd
            },
            success:function (data) {
               if(data.codice===900){
                    var $toastContent = $('<span>Email o password errati</span>');
                    Materialize.toast($toastContent, 3000);
                    
                }else if(data.codice===910){
                    $('#form').closeModal();
                    window.location.replace("index.jsp");
                }else if(data.codice===920){
                    $('#form').closeModal();
                    window.location.replace("index.jsp");
                }          
            }
            });
            document.getElementById("email").value = "";
            document.getElementById("password").value = "";
        }
    });
    
    
});

        
