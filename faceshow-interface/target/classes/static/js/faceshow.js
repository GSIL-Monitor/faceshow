//密码
$(function () {

    $('input').eq(1).keyup(function () {
        if ($(this).val().length == 0) {
            $("#p_pas1").html("密码为不能空！");
            $("#btn_submit").attr('disabled', true).addClass("full_bg");
        } else if ($(this).val().length > 5 && $(this).val().length < 19) {
            $("#p_pas1").html("");
            $("#btn_submit").attr('disabled', false);
        } else {
            $("#p_pas1").html("长度只能在6-18个字符之间");
            $("#btn_submit").attr('disabled', true);
        }
    })
//	确认密码
    $('input').eq(2).keyup(function () {

        var pas1 = $("#pas1").val();
        if (pas1 == null || pas1.length == 0) {
            $("#p_pas1").html("密码为不能空！");
            return;
        }

        if ($(this).val() != $('input').eq(1).val()) {
            $("#p_pas2").html("两次密码不匹配");
            $("#btn_submit").attr('disabled', true);
        } else {
            $("#p_pas2").html("");
            $("#btn_submit").attr('disabled', false);
        }
    })

    $("#btn_submit").click(function () {
        var pas1 = $("#pas1").val();
        var pas2 = $("#pas2").val();
        if (pas1 == null || pas1.length == 0) {
            $("#p_pas1").html("密码为不能空！");
            return;
        }
        else if (pas1.length < 6 || pas1.length > 18) {
            $("#p_pas1").html("长度只能在6-18个字符之间");
            return;
        }

        else if (pas1 != pas2) {
            $("#p_pas2").html("两次密码不匹配");
            return;
        }

        var str = Math.random().toString(36).substr(2).substring(0, 6) + pas1 + Math.random().toString(36).substr(2).substring(0, 6);

        $.post("/faceshow/api/user/register/retrievePwd", {
            "type": 2,
            "pwd": str,
            "param": $("#pas").val(),
            "token": $("#token").val()
        }, function (data) {
            if (data.code == 1 || data.code == 2) {
                // 密码修改成功
                location.href = "/faceshow/api/page/result.html?email=" + data.result + "&msg=" + data.msg;
            }else{
                alert(data.msg);
            }
        }, "json")

    })
});

//直播点击
function openModal() {

    var ua = window.navigator.userAgent.toLowerCase();

    if (ua.indexOf('micromessenger') > 0) {
        down_mask.style.display = 'block';
        document.getElementById('down_mask').onclick = function () {
            down_mask.style.display = 'none';

        };

    } else {
        // 唤醒APP
        if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
            window.location.href = "FaceShowApp://";//ios app
            window.setTimeout(function () {
                window.location.href = "https://itunes.apple.com/cn/app/id477927812";
            })
        }

        if (navigator.userAgent.match(/android/i)) {
            window.location.href = "myscheme://com.guochao.faceshow";//android app
            window.setTimeout(function () {
                window.location.href = "https://itunes.apple.com/cn/app/id477927812";
            })

        }
    }

}
	
	