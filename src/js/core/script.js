'use strict';

var loginForm = $('.login-form'),
  username = $('#username'),
  loader = $('#section-block'),
  password = $('#password');

let URL_DEFAULT = 'http://localhost:8080/api/v1';

var TOKEN = localStorage.getItem('token');

UILoading(function () {});

$(window).on('load', function () {
  if (TOKEN) {
    window.open('index.html', '_self');
  }
});

$(function () {
  if (loginForm.length) {
    loginForm.validate({
      rules: {
        username: {
          required: true,
        },
        password: {
          required: true,
        },
      },
      messages: {
        username: 'Username is required',
        password: 'password is required',
      },
    });

    loginForm.on('submit', function (e) {
      e.preventDefault();
      $('#btn-login').attr('disabled', 'disabled');

      var isValid = loginForm.valid();
      if (isValid) {
        var username_val = $('#username').val();
        var password_val = $('#password').val();

        const data = {
          userName: username_val,
          password: password_val,
        };
        loginUser(data);
      }
    });
  }
});

/*
Start:API Endpoints
*/

// authenticate login user

async function loginUser(data) {
  let URL = `${URL_DEFAULT}/authenticate`;

  let config = {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await axios.post(URL, data, config);
    if (response.status == 200) {
      localStorage.setItem('token', response.data);
      location.replace('index.html');
    }
  } catch (error) {
    if (error) {
      showToaster('Username/Password incorrect!', 'error');
      $('#btn-login').removeAttr('disabled');
    }
  }
}

/*
End: API Endpoints
*/

// show toater
function showToaster(message, type) {
  toastr[type](message, 'gfh', {
    closeButton: true,
    tapToDismiss: false,
    timeOut: 1000,
    preventDuplicates: true,
  });
}

//show UI Blocking
function UILoading() {
  $(loader).block({
    message:
      '<div class="d-flex justify-content-center align-items-center"><p class="mr-50 mb-0 text-white">Please wait...</p><div class="spinner-grow spinner-grow-sm text-white" role="status"></div>',
    css: {
      backgroundColor: 'transparent',
      border: '0',
      textAlign: 'center',
    },
    overlayCSS: {
      backgroundColor: 'black',
      minHeight: '100vh',
      opacity: 1,
    },
    timeout: 1000,
  });
}

//hide UI Blocking
function UnblockUI() {
  $(loader).unblock();
}
