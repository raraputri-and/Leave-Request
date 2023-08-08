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
                    <a href="/leave-request/reject/${data.id}" class="btn btn-danger px-3 py-2" type="button">Reject No Modal</a>
                    <button class="btn btn-danger reject-btn action" data-bs-toggle="modal" data-bs-target="#rejectModal" data-id="${data.id}">Reject</button>
                        <button class="btn btn-success accept-btn action" data-id="${data.id}" >Accept</button>
                    `;
                }
            }
        ]
    });

});


$('#table-action').on('click', '.reject-btn', function () {
    console.log('reject clicked');
    var id = $(this).data('id');
    $('#rejectModal').modal('show');
    $('#sendReject').data('id', id); // store id for later use
});

$('#sendReject').on('click', function (e) {
    e.preventDefault();
    var id = $(this).data('id');
    var note = $('#note').val();
    $.ajax({
        url: '/api/leave-request/reject/' + id, // replace with your endpoint
        type: 'PUT',
        data: { note: note },
        success: function () {
            $('#rejectModal').modal('hide');
            table.ajax.reload(); // reload data
        },
        error: function () {
            // handle error
        }
    });
});
