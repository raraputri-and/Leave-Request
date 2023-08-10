var table;

$(document).ready(function () {
    table = $('#table-action').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/action', // replace with your endpoint
            dataSrc: '' // use this if your data is an array
        },
        columns: [
            { data: 'id' },
            { data: 'employee.name', title: 'Employee' },
            { data: 'leaveType.name', title: 'Leave Type' },
            {
                data: 'dateStart',
                title: 'Start Date',
                render: function (data, type, row) {
                    var date = new Date(data);
                    var options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                    return date.toLocaleDateString('en-GB', options).split('/').join('-');
                }
            },
            {
                data: 'dateEnd',
                title: 'End Date',
                render: function (data, type, row) {
                    var date = new Date(data);
                    var options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                    return date.toLocaleDateString('en-GB', options).split('/').join('-');
                }
            },
            { data: 'quantity', title: 'Qty' },
            { data: 'reason', title: 'Reason' },
            { data: 'attachment', title: 'Attachment' },
            { data: 'statusAction.name', title: 'Status' },
            {
                data: null,
                title: 'Action',
                render: function (data, type, row) {
                    return `
            <div class="d-flex gap-3">
                <button class="btn btn-danger px-3 py-2" type="button" data-bs-toggle="modal" 
                    data-bs-target="#rejectModal" onclick="openRejectModal(${data.id})"><span
                class="bi bi-pencil-square">Reject</button>

                    <form th:action="" th:method="PUT" onsubmit="return acceptRequest(event, '${row.id}')">
                    <button type="submit" class="btn btn-success px-3 py-2">Accept</button>
                    </form>
            </div>
                    

                    `;
                }
            }
        ]
    });

});


function openRejectModal(id) {
    $("#rejectModal").attr("data-reject-id", id);
    $("#rejectModal").modal("show");
}

// update country
function rejectNote() {
    let rejectId = $("#rejectModal").attr("data-reject-id")
    let noteVal = $("#note").val()
    console.log(note)
    $.ajax({
        method: "PUT",
        url: `/api/leave-request/reject/${rejectId}`,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
            note: noteVal,
        }),
        contentType: "application/json",
        success: function (result) {
            $("#rejectModal").modal('hide')
            $("#table-action").DataTable().ajax.reload()
            $("#note").val('')
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Leave Request has been Rejected',
                showConfirmButton: false,
                timer: 2000
            })
        }
    })
}

function acceptRequest(event, id) {
    event.preventDefault();
    // fetch the row data to get the employee name
    var rowData = table.row($(event.target).parents('tr')).data();
    Swal.fire({
        title: "Accept",
        text: "Are you sure you want to accept request from : " + rowData.employee.name + "?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "green",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Yes, accept it it!",
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                method: "PUT",
                url: `/api/leave-request/accept/${id}`,
                dataType: "JSON",
                contentType: "application/json",
                beforeSend: addCsrfToken(),
                success: function (result) {
                    $("#table-action").DataTable().ajax.reload();
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Leave Request has been Accepted',
                        showConfirmButton: false,
                        timer: 2000
                    });
                },
            });
        }
    });
}


// $('#table-action').on('click', '.reject-btn', function () {
//     console.log('reject clicked');
//     var id = $(this).data('id');
//     $('#rejectModal').modal('show');
//     $('#sendReject').data('id', id); // store id for later use
// });

// $('#sendReject').on('click', function (e) {
//     e.preventDefault();
//     var id = $(this).data('id');
//     var note = $('#note').val();
//     $.ajax({
//         url: '/api/leave-request/reject/' + id, // replace with your endpoint
//         type: 'PUT',
//         data: { note: note },
//         success: function () {
//             $('#rejectModal').modal('hide');
//             table.ajax.reload(); // reload data
//         },
//         error: function () {
//             // handle error
//         }
//     });
// });