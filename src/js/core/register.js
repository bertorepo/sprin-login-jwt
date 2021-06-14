var formRegister = $('.register-form'),
  username = $('#username'),
  firstName = $('#firstName'),
  lastName = $('#lastName'),
  loader = $('#section-block'),
  password = $('#password');

let URL_DEFAULT = 'http://localhost:8080/api/v1';

$(function () {
  'use-strict';

  if (formRegister.length) {
    // validate form fields
    formRegister.validate({
      rules: {
        username: {
          required: true,
        },
        firstName: {
          required: true,
        },
        lastName: {
          required: true,
        },
        password: {
          required: true,
        },
      },
      messages: {
        username: 'Username is required',
        firstName: 'FirstName is required',
        lastName: 'LastName is required',
        password: 'Password is required',
      },
    });

    //when submitting the form

    formRegister.on('submit', function (e) {
      e.preventDefault();

      var isValid = formRegister.valid();
      if (isValid) {
        UILoading();
        $('#btn-register').attr('disabled', 'disabled');
        var username_val = username.val();
        var firstName_val = firstName.val();
        var lastName_val = lastName.val();
        var password_va = password.val();
        const data = {
          userName: username_val,
          firstName: firstName_val,
          lastName: lastName_val,
          password: password_va,
        };

        registerUser(data);
      }
    });
  }

  /*
    start: HANDLE API RESPONSE
  */

  //register the user

  async function registerUser(data) {
    let config = {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    };
    let URL = `${URL_DEFAULT}/register`;

    try {
      const response = await axios.post(URL, data, config);
      if (response.status == 200) {
        showToaster('success', 'success', function () {
          location.replace('login.html');
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  /*
    End: HANDLE API RESPONSE 
  */

  // show toater
  function showToaster(message, type, cb) {
    toastr[type](message, '💾 Successfull!', {
      closeButton: true,
      tapToDismiss: false,
      timeOut: 1000,
      preventDuplicates: true,
      onHidden: function () {
        cb();
      },
    });
  }

  //show UI Blocking
  function UILoading() {
    $(loader).block({
      message: '<div class="spinner-border text-white" role="status"></div>',
      css: {
        backgroundColor: 'transparent',
        border: '0',
      },
      overlayCSS: {
        opacity: 0.5,
      },
      timeOut: 2000,
    });
  }

  //hide UI Blocking
  function UnblockUI() {
    $(loader).unblock();
  }
});
