$(document).ready(function () {
    $('#table-user').DataTable({
        ajax: {
            url: '/api/emp',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'nip'},
            { data: 'name'},
            { data: 'email'},
            { data: 'joinDate',
                render: function (data, type, row) {
                    var date = new Date(data);
                    var options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                    return date.toLocaleDateString('en-GB', options).split('/').join('-');
                }
                },
            { data: 'gender'},
            { data: 'religion.name'},
            { data: 'manager.name'},
            { data: 'username'},
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#updateEmployee" onclick="beforeUpdate('${row.id}')"
                            class="btn btn-warning px-5 py-2 me-3">Edit</button>
                    </div>`
                }
            }
        ]
    });
    religionSelector()
    function religionSelector() {
        $.ajax({
            url: "/api/religion",
            method: "GET",
            dataType: "JSON",
            success: function (data) {
                let selectReligion = $(".religion-selector");

                $.each(data, function (index, religion) {
                    selectReligion.append(`<option value="${religion.id}"> ${religion.name} </option>`)
                })
            }
        })
    }

    employeeSelector()
    function employeeSelector() {
        $.ajax({
            url: "/api/emp",
            method: "GET",
            dataType: "JSON",
            success: function (data) {
                let selectEmp = $(".employee-selector");

                $.each(data, function (index, emp) {
                    selectEmp.append(`<option value="${emp.id}"> ${emp.name} </option>`)
                })
            }
        })
    }
});

function beforeUpdate(id){
    $.ajax({
            method: "GET",
            url: "/api/emp/" + id,
            dataType: "JSON",
            beforeSend: addCsrfToken(),
            success: (result) => {
                $("#id").val(result.id)
                $("#nip").val(result.nip)
                $("#name").val(result.name)
                $("#email").val(result.email)
                const inputDate = new Date(result.joinDate);

// Extract year, month, and day from the Date object
                const year = inputDate.getFullYear();
                const month = String(inputDate.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
                const day = String(inputDate.getDate()).padStart(2, '0');

// Format the date parts into the desired output format
                const outputDateString = `${year}-${month}-${day}`;
                $("#joinDate").val(outputDateString)
                $("#gender").val(result.gender)
                $("#religion").val(result.religion.id)
                $("#manager").val(result.manager.id)
                $("#username").val(result.username)
            }
        }
    )
}

function editEmployee() {
    let idVal = $("#id").val()
    let nipVal = $("#nip").val()
    let nameVal = $("#name").val()
    let emailVal = $("#email").val()
    let joinDateVal = $("#joinDate").val()
    let genderVal = $("#gender").val()
    let religionVal = $("#religion").val()
    let managerVal = $("#manager").val()
    let usernameVal = $("#username").val()
    let passwordVal = $("#password").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this employee?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, update it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/api/emp/update/" + idVal,
                method: "PUT",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                data: JSON.stringify({
                    nip : nipVal,
                    name : nameVal,
                    email : emailVal,
                    joinDate : joinDateVal,
                    gender: genderVal,
                    religionId : religionVal,
                    managerId : managerVal,
                    username : usernameVal,
                    password : passwordVal
                }),
                contentType: "application/json",
                success: (result) => {
                    $("#table-user").DataTable().ajax.reload()
                    $("#updateEmployee").modal('hide')
                    $("#password").val('')
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Employee has been updated',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}

function createEmployee() {
    let nipVal = $("#nip2").val()
    let nameVal = $("#name2").val()
    let emailVal = $("#email2").val()
    let joinDateVal = $("#joinDate2").val()
    let genderVal = $("#gender2").val()
    let religionVal = $("#religion2").val()
    let managerVal = $("#manager2").val()
    let usernameVal = $("#username2").val()
    let passwordVal = $("#password2").val()
    $.ajax({
        method: "POST",
        url: "/api/emp",
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
            nip : nipVal,
            name : nameVal,
            email : emailVal,
            joinDate : joinDateVal,
            gender: genderVal,
            religionId : religionVal,
            managerId : managerVal,
            username : usernameVal,
            password : passwordVal
        }),
        contentType: "application/json",
        success: function (result) {
            $("#createEmployee").modal('hide')
            $("#table-user").DataTable().ajax.reload()
            $("#nip2").val('')
            $("#name2").val('')
            $("#email2").val('')
            $("#joinDate2").val('')
            $("#gender2").val('')
            $("#religion2").val('')
            $("#manager2").val('')
            $("#username2").val('')
            $("#password2").val('')
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Employee has been created',
                showConfirmButton: false,
                timer: 2000
            })
        }
    })
}