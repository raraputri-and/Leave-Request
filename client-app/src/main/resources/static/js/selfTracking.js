$(document).ready(function () {
    table = $('#table-tracking').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/user-tracking', // replace with your endpoint
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
            { data: 'attachment', title: 'Attachment' },
            { data: 'statusAction.name', title: 'Status' },
            {
                data: null,
                title: 'Action',
                render: function (data, type, row) {
                    return `
                    <div class= "d-flex gap-2">
                        <button class="btn btn-info px-3 py-2" type="button" data-bs-toggle="modal" 
                        data-bs-target="#pastActionsModal" onclick="showPast(${data.id})"><span
                        class="bi bi-pencil-square">Show</button>
                    </div>
                    

                    `;
                }
            }
        ]
    });

//     function loadPastActions(leaveRequestId) {
//         $.ajax({
//             type: 'GET',
//             url: '/api/leave-request/${leaveRequestId}',
//             dataType: 'json',
//             success: function(pastActions) {
//                 console.log(leaveRequestId)
//                 const modalTable = $('#pastActionsTable').DataTable();
//                 modalTable.clear().draw();
//
//                 pastActions.forEach(function(action) {
//                     modalTable.row.add([
//                         action.note,
//                         action.date,
//                         action.pic.name,
//                         action.statusAction.name
//                     ]).draw(false);
//                 });
//             },
//             error: function() {
//                 console.log('Error fetching past actions.');
//             }
//         });
//     }
// });
});


// function openModal(id) {
//     $("#pastActionsModal").attr("data-past-id", id);
//     $("#pastActionsModal").modal("show");
// }

function showPast(id) {

    $.ajax({
        method: "GET",
        url: `/api/leave-request-status/get-leave-request/${id}`,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        success: function (data) {
            if (data.length > 0) {
                let listItemHTML = $("#list");
                $('#list').empty()
                $.each(data, function (index, result) {
                    listItemHTML.append('<li class="activity-list activity-border">' +
                        '<div class="activity-icon avatar-sm">' +
                    '<img src="' + result.pic + '" class="avatar-sm rounded-circle" alt="">' +
                    '</div>' + '<div className = "media" >' +'<div class="me-3">' + '<p class="text-muted font-size-14 mb-0">' + result.date + '</p>'
                        + '<p class="text-muted font-size-14 mb-0">' + result.leaveRequest.id + '</p>' + '<p class="text-muted font-size-14 mb-0">' + result.note + '</p>' +
                        '<p class="text-muted font-size-14 mb-0">' + result.pic.name + '</p>' + '<p class="text-muted font-size-14 mb-0">' + result.statusAction.name + '</p>' +
                        '</div>' + '<div class="media-body">' + '<div class="text-end d-none d-md-block">' + '</div>' + '</div>' + '</div>' + '</li>'

                    )

                });


            $("#pastActionsModal").modal("show");
        }
    }
});
}

// function showModalDetail() {
//     let leaveId = $("#pastActionsModal").attr("data-past-id")

//     $.ajax({
//         method: "GET",
//         url: `/api/leave-request-status/get-leave-request/${leaveId}` ,
//         dataType: "JSON",
//         beforeSend: addCsrfToken(),
//         success: function (result) {
//             // Konstruksi teks yang akan ditampilkan di elemen <p>
//             let regionInfo = `
//             <table class="table">
//               <tbody>
//                 <tr>
//                   <th scope="row">Region ID:</th>
//                   <td>${result.id}</td>
//                 </tr>
//                 <tr>
//                   <th scope="row">Name:</th>
//                   <td>${result.name}</td>
//                 </tr>
//               </tbody>
//             </table>
//           `;
//             // Set teks data ke dalam elemen <p>
//             $("#region-info").html(regionInfo);

//             $("#pastActionsModal").modal("show");
//         }
//     });
// }

// $(document).ready(function() {
//     $('#table-tracking').DataTable();

//     $('.show-past-actions-btn').click(function() {
//         const leaveRequestId = $(this).attr('data-lr-id');
//         $('#pastActionsModal').modal('show');
//         loadPastActions(leaveRequestId);
//     });

//     function loadPastActions(leaveRequestId) {
//         $.ajax({
//             type: 'GET',
//             url: '/api/leave-request-status/' + leaveRequestId,
//             dataType: 'json',
//             success: function(pastActions) {
//                 console.log('Received past actions:', pastActions);
//                 const modalTable = $('#pastActionsTable').DataTable();
//                 modalTable.clear().draw();

//                 pastActions.forEach(function(action) {
//                     modalTable.row.add([
//                         action.note,
//                         action.date,
//                         action.pic.name,
//                         action.statusAction.name
//                     ]).draw(false);
//                 });
//             },
//             error: function() {
//                 console.log('Error fetching past actions.');
//             }
//         });
//     }
// });
