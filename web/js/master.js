$( document ).ready(function(){
    
   $('.formlogin input').keypress(function(e){
      if(e.keyCode===13)
        $('#btn_login').click();
    });
    
    $('.formregistra').keypress(function(e){
      if(e.keyCode===13)
        $('#btn_signup').click();
    });
    
    // Initialize collapse button
    $(".button-collapse").sideNav();
    
    $('.modal-trigger').leanModal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
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
    
    $("#side_btn_logout").click(function(){
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
        var email = document.getElementById("email_lgn").value;
        var pwd = document.getElementById("password_lgn").value;
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
    $("#btn_signup").click(function(){
        var name = document.getElementById("first_name").value;
        var email = document.getElementById("email_sgnup").value;
        var pwd = document.getElementById("password_sgnup").value;
        if(email==="" || pwd === "" || name==""){
            var $toastContent = $('<span>Tutti i campi sono obbligatori</span>');
            Materialize.toast($toastContent, 3000);
        }else{
            $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "signup",
                email: email,
                name: name,
                pwd: pwd
            },
            success:function (data) {
                $('#form').closeModal();
                alert("Ti è stata inviata una e-mail al tuo indirizzo. Controlla la posta.");        
            }
            });
            document.getElementById("email_sgnup").value = "";
            document.getElementById("password_sgnup").value = "";
            document.getElementById("first_name").value = "";

        }
    });
    
    
});

        
