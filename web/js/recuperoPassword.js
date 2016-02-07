$(document).ready(function() {
    $(".button-collapse").sideNav();
    $('#btn_send').click(function(){
        var email = document.getElementById("email").value
        if(email===""){
            var $toastContent = $('<span>Inserire una email valida</span>');
            Materialize.toast($toastContent, 3000);
        }else{
            $.ajax({
            type : 'POST',
            url : 'Controller',           
            data: {
                op : "pswdimenticata",
                email: email
            },
            success:function (data) {
                if(data===1)
                    $('#form').append('<p>Email inviata, controlla la tua casella di posta</p>')
                else
                    $('#form').append('<p>Non è registrato alcun profilo con quell email</p>')
            }
            })
        }
    })
});


