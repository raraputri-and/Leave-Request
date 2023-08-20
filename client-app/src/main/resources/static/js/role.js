$(document).ready(function () {
    $('#table-role').DataTable({
        ajax: {
            url: '/api/user',
            dataSrc: ''
        },
        columns: [
            {
                data: null,
                title: 'No'
            },
            {
                data: 'id',
                visible: false
            },
            { data: 'username' },
            { data: 'roles[0].name' },
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#editModal" onclick="beforeUpdate('${row.id}')"
                            class="btn btn-warning px-5 py-2 me-3">Edit</button>
                    </div>`
                }
            }
        ],
        rowCallback: function (row, data, index) {
            $('td:eq(0)', row).html(index + 1); // Assuming "No" column is the first column
        }
    });
});
roleSelector()
function roleSelector() {
    $.ajax({
        url: "/api/role",
        method: "GET",
        dataType: "JSON",
        success: function (data) {
            let selectRole = $(".role-selector");

            $.each(data, function (index, role) {
                selectRole.append(`<option value="${role.id}"> ${role.name} </option>`)
            })
        }
    })
}

function beforeUpdate(id) {
    $.ajax({
        method: "GET",
        url: "/api/user/" + id,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        success: (result) => {
            $("#idUser").val(result.id)
            $("#id").val(result.username)
            $("#role").val(result.roles[0].id)
        }
    }
    )
}

function editRole() {
    let idVal = $("#idUser").val()
    let roleVal = $("#role").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this role?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, update it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `/api/user/update/${idVal}?roleId=${roleVal}`,
                method: "PUT",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                contentType: "application/json",
                success: (result) => {
                    $("#table-role").DataTable().ajax.reload()
                    $("#editModal").modal('hide')
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Role has been updated',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}