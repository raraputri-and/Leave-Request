var table;

$(document).ready(function () {
    table = $('#table-action').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/action',
            dataSrc: ''
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

                    <div class="d-flex gap-2">
                <button class="btn btn-primary px-3 py-2" type="button"
                    data-bs-toggle="modal" data-bs-target="#actionModal" onclick="openActionModal(${data.id})">
                    Action
                </button>
            </div>
                    

                    `;
                }
            }
        ],
    });

});


// function openRejectModal(id) {
//     $("#rejectModal").attr("data-reject-id", id);
//     $("#rejectModal").modal("show");
// }

function openActionModal(id) {
    let rowIndex = table.row($('#table-action tr[data-id="' + id + '"]')).index(); // Get the row index based on the data-id attribute
    let rowData = table.row(rowIndex).data(); // Get the row data using the index

    if (rowData) {
        $("#actionModal").attr("data-action-id", id);
        $("#employeeName").text(rowData.employee.name); // Update the employee name in the modal
        $("#leaveType").text(rowData.leaveType.name); // Update the leave type in the modal
        // You can continue updating other modal elements with the relevant data

        $("#actionModal").modal("show");
    }
}



function rejectNote() {
    let rejectId = $("#actionModal").attr("data-action-id")
    let noteVal = $("#actionNote").val()
    console.log(noteVal)
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
            $("#actionModal").modal('hide')
            $("#table-action").DataTable().ajax.reload()
            $("#actionNote").val('')
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

function acceptNote() {
    let acceptId = $("#actionModal").attr("data-action-id")
    let noteVal = $("#actionNote").val()
    console.log(noteVal)
    $.ajax({
        method: "PUT",
        url: `/api/leave-request/accept/${acceptId}`,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
            note: noteVal,
        }),
        contentType: "application/json",
        success: function (result) {
            $("#actionModal").modal('hide')
            $("#table-action").DataTable().ajax.reload()
            $("#actionNote").val('')
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Leave Request has been Accepted',
                showConfirmButton: false,
                timer: 2000
            })
        }
    })
}

function acceptRequest(event) {
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
            let acceptId = $("#actionModal").attr("data-action-id")
            let noteVal = $("#actionNote").val()
            $.ajax({
                method: "PUT",
                url: `/api/leave-request/accept/${acceptId}`,
                dataType: "JSON",
                data: JSON.stringify({
                    note: noteVal,
                }),
                contentType: "application/json",
                beforeSend: addCsrfToken(),
                success: function (result) {
                    $("#actionModal").modal('hide')
                    $("#table-action").DataTable().ajax.reload();
                    $("#actionNote").val('')
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

