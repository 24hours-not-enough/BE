var stompClient = null;
var planId = 9
var userId = 1
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

//채팅방 입장
function connect() {
    var socket = new SockJS('/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+planId, function (e) {
            //리턴해주는 값의 속성 값 담아주기
            showMsg(JSON.parse(e.body).message);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    data = {'message': $("#name").val(), 'user_id':userId, 'plan_id':planId};
    stompClient.send("/app/chat/"+planId, {}, JSON.stringify(data));
}

function showMsg(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});


//사용자 큐 입장 -> 토큰 검사 후 입장(로그인 한 동안 계속)
function connect1() {
    var socket = new SockJS('/alarmpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        // userId는 본인의 userId 값으로 담아낼 것
        stompClient.subscribe('/queue/' + userId, function (e) {
            //리턴해주는 값의 속성 값 담아주기
            showMsg(JSON.parse(e.body).message);
        });
    });
}

function disconnect1() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendComment() {
    // 카테고리, from_user 정보, 해당 게시글이나 Plan에 대한 정보
    data = {'message': $("#comment").val(), 'planId':1, 'planName':"가즈아", "receiveUserId" : 1, 'category': 'comment'};
    stompClient.send("/app/notification/"+userId, {}, JSON.stringify(data));
}

function sendInvite() {
    data = {'message': $("#InviteBtn").val(), 'user_id':userId, 'category': 'invite'};
    stompClient.send("/app/notification/"+userId, {}, JSON.stringify(data));
}
// 알림 유저 여러명? -> 어떻게 처리하지? -> topic으로 가야하나
function sendChat() {
    data = {'message': $("#ChatBtn").val(), 'user_id':userId, 'category': 'chat'};
    stompClient.send("/app/notification/"+userId, {}, JSON.stringify(data));
}

function showMsg(message) {
    $("#greetings1").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect1" ).click(function() { connect1(); });
    $( "#disconnect1" ).click(function() { disconnect1(); });
    $( "#send1" ).click(function() { sendComment(); });
});
