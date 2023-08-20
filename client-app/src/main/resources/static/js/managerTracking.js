$(document).ready(function () {
    table = $('#table-tracking-employee').DataTable({
        destroy: true,
        ajax: {
            url: '/api/leave-request/manager-tracking', // replace with your endpoint
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
            // { data: 'statusAction.name', title: 'Status' },
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
        ],
        rowCallback: function (row, data, index) {
            $('td:eq(0)', row).html(index + 1); // Assuming "No" column is the first column
        }
    })
});