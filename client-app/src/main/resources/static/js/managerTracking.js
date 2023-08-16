$(document).ready(function () {
    table = $('#table-tracking-employee').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/manager-tracking', // replace with your endpoint
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
                    console.log(data)
                    if (data.attachment!==''){
                        return `<div class="d-flex gap-2">
                        <button class="btn btn-info px-3 py-2" type="button" data-bs-toggle="modal" 
                        data-bs-target="#attachment" onclick="openAttachmentManager(${data.id})"><span
                        class="bi bi-pencil-square">Attachment
                        </button>
                        </div>`
                    }
                    return ``;
                }
            }
        ]
    })
});

function openAttachmentManager(id){
    let url = `/api/leave-request/attachment/${id}`
    $('#image').attr('src', url)
}