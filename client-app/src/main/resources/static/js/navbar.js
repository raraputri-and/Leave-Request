document.addEventListener("DOMContentLoaded", function (event) {

    const toggle = document.getElementById('header-toggle'),
        nav = document.getElementById('nav-bar'),
        bodypd = document.getElementById('body-pd'),
        headerpd = document.getElementById('header');

    // Retrieve the sidebar state from localStorage
    const savedSidebarState = localStorage.getItem('sidebarState');

    // Apply the appropriate class based on the saved state
    if (savedSidebarState === 'open') {
        nav.classList.add('show');
        toggle.classList.add('bx-x');
        bodypd.classList.add('body-pd');
        headerpd.classList.add('body-pd');
    }

    // Toggle the sidebar state and store it in localStorage
    toggle.addEventListener('click', () => {
        nav.classList.toggle('show');
        toggle.classList.toggle('bx-x');
        bodypd.classList.toggle('body-pd');
        headerpd.classList.toggle('body-pd');

        const sidebarState = nav.classList.contains('show') ? 'open' : 'closed';
        localStorage.setItem('sidebarState', sidebarState);
    });

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link');

    function colorLink() {
        linkColor.forEach(l => l.classList.remove('active'));
        this.classList.add('active');
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink));

    // Your code to run since DOM is loaded and ready
});

$(document).ready(function () {
    $('#table-action').DataTable();
});

$(document).ready(function () {
    $('#table-tracking').DataTable();
});

function logout(event, id) {
    Swal.fire({
        title: 'Confirmation Logout',
        text: 'Are You Sure Want to Logout?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/login/logout';
        }
    });
}
