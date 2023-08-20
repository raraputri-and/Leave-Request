$(document).ready(function () {
    $('#table-leave-remaining').DataTable({
        ajax: {
            url: '/api/leave-remaining',
            dataSrc: ''
        },
        columns: [
            { data: null, title: 'No' },
            { data: 'employee.name' },
            { data: 'pastRemaining' },
            { data: 'presentRemaining' },
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

function beforeUpdate(id) {
    $.ajax({
        method: "GET",
        url: "/api/leave-remaining/" + id,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        success: (result) => {
            $("#idLeaveRemaining").val(result.id)
            $("#id").val(result.employee.name)
            $("#pastRemaining").val(result.pastRemaining)
            $("#presentRemaining").val(result.presentRemaining)
        }
    }
    )
}

function editLeaveRemaining() {
    let idVal = $("#idLeaveRemaining").val()
    let pastVal = $("#pastRemaining").val()
    let presentVal = $("#presentRemaining").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this leave remaining?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, update it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/api/leave-remaining/update/" + idVal,
                method: "PUT",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                data: JSON.stringify({
                    pastRemaining: pastVal,
                    presentRemaining: presentVal
                }),
                contentType: "application/json",
                success: (result) => {
                    $("#table-leave-remaining").DataTable().ajax.reload()
                    $("#editModal").modal('hide')
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Leave Remaining has been updated',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}