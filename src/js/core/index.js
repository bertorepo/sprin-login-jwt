'use-strict';

let TOKEN = localStorage.getItem('token');
var loader = $('#section-block');

$(function () {
  UILoading(function () {
    if (!TOKEN) {
      window.open('login.html', '_self');
    } else {
      renderDashboard();
    }
  });
});
//logout

$(document).on('click', '#logout', function () {
  TOKEN = localStorage.removeItem('token');
  location.replace('login.html');
});

// api
//index

async function renderDashboard() {
  let URL = 'http://localhost:8080/api/v1/';

  try {
    const response = await axios.get(URL, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${TOKEN}`,
      },
    });
    if (response.status == 200) {
      $('#fullname').text(response.data.fullName);
    }
  } catch (error) {
    console.log(error);
  }
}

// show toater
function showToaster(message, type) {
  toastr[type](message, 'Notifications', {
    closeButton: true,
    tapToDismiss: false,
    timeOut: 1000,
    preventDuplicates: true,
  });
}

//show UI Blocking
function UILoading(cb) {
  $(loader).block({
    message:
      '<div class="d-flex justify-content-center align-items-center"><p class="mr-50 mb-0 text-white">Please wait...</p><div class="spinner-grow spinner-grow-sm text-white" role="status"></div>',
    css: {
      backgroundColor: 'transparent',
      border: '0',
      margin: 'auto',
    },
    overlayCSS: {
      opacity: 1,
      backgroundColor: '#000',
      minHeight: '100vh',
      margin: 'auto',
    },
    timeout: 1000,
    onUnblock: function () {
      cb();
    },
  });
}

//hide UI Blocking
function UnblockUI() {
  $(loader).unblock();
}
