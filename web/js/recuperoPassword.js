$(document).ready(function() {
    $('.button-collapse').sideNav();
    $('#btn_send').click(function(){
        $('#error').empty()
        var email = document.getElementById("email").value
        if(email===""){
            var $toastContent = $('<span>Inserire una email valida</span>')
            Materialize.toast($toastContent, 3000)
        }else{
            $.ajax({
                type : 'POST',
                url : 'Controller',           
                data: {
                    op : 'pswdimenticata',
                    email: email
                },
                success:function (data) {
                    if(data==='1')
                        $('#error').append('<p>Email inviata, controlla la tua casella di posta</p>')
                    else
                        $('#error').append('<p>Non è registrato alcun profilo con quell email</p>')
                }
            })
            document.getElementById('email').value = ''
        }
    })
});


