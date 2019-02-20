
function getCommonObject(){
    return {
        ajax:{
            get:function (url,success) {
                $.ajax({
                   url:url,
                   method:"GET",
                   success:success,
                   error:function (response) {
                       alert("数据请求出错");
                       console.log(response);
                   }
                });
            }
            ,
            post:function (url,success,data) {
                $.ajax({
                    url:url,
                    method:"POST",
                    headers:{"Content-Type":"application/json"},
                    data:JSON.stringify(data),
                    success:success,
                    error:function (response) {
                        alert("数据请求出错");
                        console.log(response);
                    }
                });
            }
            ,
            put:function (url,success,data) {
                $.ajax({
                    url:url,
                    method:"PUT",
                    headers:{"Content-Type":"application/json"},
                    data:JSON.stringify(data),
                    success:success,
                    error:function (response) {
                        alert("数据请求出错");
                        console.log(response);
                    }
                });
            }
        },
        data:{
            loginUrl:'/user/login',
            registerUrl:'/user/register',
            logoutUrl:'/user/logout',
            getCurrentUserDataUrl:'/user/state',
            getCurrentUserInfoUrl:"/userInfo/self",
            updateUserInfoUrl:'/userInfo'
        }
    }
}

var common = getCommonObject();