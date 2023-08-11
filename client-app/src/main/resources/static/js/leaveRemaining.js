$(document).ready(function () {
    $('#table-leave-remaining').DataTable({
        ajax: {
            url: '/api/leave-remaining',
            dataSrc: ''
        },
        columns: [
            { data: 'id'},
            { data: 'pastRemaining'},
            { data: 'presentRemaining'},
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#editModal" onclick="beforeUpdate('${row.id}')"
                            class="btn btn-warning px-5 py-2 me-3">Edit</button>
                    </div>`
                }
            }
        ]
    });
});

function beforeUpdate(id){
    $.ajax({
            method: "GET",
            url: "/api/leave-remaining/" + id,
            dataType: "JSON",
            beforeSend: addCsrfToken(),
            success: (result) => {
                $("#id").val(result.id)
                $("#pastRemaining").val(result.pastRemaining)
                $("#presentRemaining").val(result.presentRemaining)
            }
        }
    )
}

function editLeaveRemaining() {
    let idVal = $("#id").val()
    let pastVal = $("#pastRemaining").val()
    let presentVal = $("#presentRemaining").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this region?",
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