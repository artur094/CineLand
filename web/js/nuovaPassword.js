$(document).ready(function() {
    $(".button-collapse").sideNav();
    $('#btn_send').click(function(){
        $('#error').empty()
        var pass1 = document.getElementById("pass").value
        var pass2= document.getElementById("npass").value
        if(pass1 !== pass2 || pass1==='' || pass2 ===''){
            var $toastContent = $('<span>ERRORE: password diverse</span>');
            Materialize.toast($toastContent, 3000);
        }else{
            $.ajax({
                type : 'POST',
                url : 'Controller',           
                data: {
                    op : "resetpsw",
                    pwd : pass1
                },
                success:function (data) {
                    if(data==="1"){
                        $('#error').append('<p>Password cambiata con successo</p>')
                        window.location.replace("/NuovoBackend/index.jsp");
                    }else{
                        $('#error').append('<p>Email inviata, controlla la tua casella di posta</p>')
                    }
                }
            })
            document.getElementById("pass").value = ''
            document.getElementById("npass").value = ''
        }
    })
});

