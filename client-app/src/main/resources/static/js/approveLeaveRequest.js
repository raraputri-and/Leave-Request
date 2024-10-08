var table;
$(document).ready(function () {
    table = $('#table-action').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/action',
            dataSrc: ''
        },
        columns: [
            {
                data: null,
                title: 'No'
            },
            { data: 'id', visible: false },
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
            {
                data: 'statusAction.name', title: 'Status',
                render: function (data, type, row) {
                    let colorClass;
                    switch (data) {
                        case 'Waiting for Approval': colorClass = 'bg-warning'; break;
                        case 'Accepted': colorClass = 'bg-success'; break;
                        case 'Rejected': colorClass =
                            'bg-danger'; break;
                        default: colorClass = 'bg-dark'; break;
                    }
                    return `<span class="badge ${colorClass}">${data}</span>`;
                }
            },
            {
                data: null,
                title: 'Action',
                render: function (data, type, row) {
                    if (data.attachment===''){
                        return `<div class="d-flex gap-2">
                        <button class="btn btn-info px-3 py-2" type="button"
                            data-bs-toggle="modal" data-bs-target="#actionModal" onclick="openActionModal(${data.id})">
                             Action
                        </button>
                        </div>`
                    }
                    return `

                    <div class="d-flex gap-2">
                    <button class="btn btn-info px-3 py-2" type="button"
                        data-bs-toggle="modal" data-bs-target="#actionModal" onclick="openActionModal(${data.id})">
                        Action
                    </button>
                    <button class="btn btn-primary px-3 py-2" type="button"
                        data-bs-toggle="modal" data-bs-target="#attachment" onclick="openAttachment(${data.id})">
                        Attachment
                    </button>
                    </div>
                    `;
                }
            }
        ],
        rowCallback: function (row, data, index) {
            $('td:eq(0)', row).html(index + 1); // Assuming "No" column is the first column
        }
    });

});


function openAttachment(id){
    let url = `/api/leave-request/attachment/${id}`
    $('#image').attr('src', url)
}

function openActionModal(id) {
    $("#actionModal").attr("data-action-id", id);

    $.ajax({
        method: "GET",
        url: `/api/leave-request/${id}`,
        dataType: "JSON",
        success: function (result) {
            $("#employeeName").text(result.employee.name);
            $("#leaveType").text(result.leaveType.name);

            var startDate = new Date(result.dateStart).toLocaleDateString('en-GB').split('/').join('-');
            $("#dateStart").text(startDate);

            var endDate = new Date(result.dateEnd).toLocaleDateString('en-GB').split('/').join('-');
            $("#dateEnd").text(endDate);

            $("#quantity").text(result.quantity);
            $("#reason").text(result.reason);

            // Update status color
            let colorClass;
            switch (result.statusAction.name) {
                case 'Waiting for Approval': colorClass = 'bg-warning'; break;
                case 'Accepted': colorClass = 'bg-success'; break;
                case 'Rejected': colorClass = 'bg-danger'; break;
                default: colorClass = 'bg-dark'; break;
            }
            $("#status").html(`<span class="badge ${colorClass}">${result.statusAction.name}</span>`);

            // Handle modal actions (accept/reject)
            $("#rejectButton").attr("data-id", id);
            $("#acceptButton").attr("data-id", id);

            $("#actionModal").modal("show");
        }
    });
}



function rejectNote() {
    let rejectId = $("#actionModal").attr("data-action-id")
    let noteVal = $("#actionNote").val()
    console.log(noteVal)
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to reject this Leave Request?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, reject it!'
    }).then((result) => {
        if (result.isConfirmed) {
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
    })
}


function acceptNote() {
    let acceptId = $("#actionModal").attr("data-action-id")
    let noteVal = $("#actionNote").val()
    console.log(noteVal)
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to accept this Leave Request?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, accept it!'
    }).then((result) => {
        if (result.isConfirmed) {
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
    })
}

// function acceptRequest(event) {
//     event.preventDefault();
//     // fetch the row data to get the employee name
//     var rowData = table.row($(event.target).parents('tr')).data();
//     Swal.fire({
//         title: "Accept",
//         text: "Are you sure you want to accept request from : " + rowData.employee.name + "?",
//         icon: "warning",
//         showCancelButton: true,
//         confirmButtonColor: "green",
//         cancelButtonColor: "#3085d6",
//         confirmButtonText: "Yes, accept it it!",
//     }).then((result) => {
//         if (result.isConfirmed) {
//             let acceptId = $("#actionModal").attr("data-action-id")
//             let noteVal = $("#actionNote").val()
//             $.ajax({
//                 method: "PUT",
//                 url: `/api/leave-request/accept/${acceptId}`,
//                 dataType: "JSON",
//                 data: JSON.stringify({
//                     note: noteVal,
//                 }),
//                 contentType: "application/json",
//                 beforeSend: addCsrfToken(),
//                 success: function (result) {
//                     $("#actionModal").modal('hide')
//                     $("#table-action").DataTable().ajax.reload();
//                     $("#actionNote").val('')
//                     Swal.fire({
//                         position: 'center',
//                         icon: 'success',
//                         title: 'Leave Request has been Accepted',
//                         showConfirmButton: false,
//                         timer: 2000
//                     });
//                 },
//             });
//         }
//     });
// }

