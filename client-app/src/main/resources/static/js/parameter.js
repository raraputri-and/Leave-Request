$(document).ready(function () {
    $('#table-parameter').DataTable({
        ajax: {
            url: '/api/parameter',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'leaveQty'},
            { data: 'note'},
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#updateParameter" onclick="beforeUpdate('${row.id}')"
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
            url: "/api/parameter/" + id,
            dataType: "JSON",
            beforeSend: addCsrfToken(),
            success: (result) => {
                $("#id").val(result.id)
                $("#leaveQty").val(result.leaveQty)
                $("#note").val(result.note)
            }
        }
    )
}

function updateParameter() {
    let idVal = $("#id").val()
    let leaveQtyVal = $("#leaveQty").val()
    let noteVal = $("#note").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this parameter?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, update it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/api/parameter/update/" + idVal,
                method: "PUT",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                data: JSON.stringify({
                    leaveQty: leaveQtyVal,
                    note: noteVal
                }),
                contentType: "application/json",
                success: (result) => {
                    $("#table-parameter").DataTable().ajax.reload()
                    $("#updateParameter").modal('hide')
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Parameter has been updated',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}