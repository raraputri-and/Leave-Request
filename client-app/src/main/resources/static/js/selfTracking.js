$(document).ready(function() {
    $('#table-tracking').DataTable();

    $('.show-past-actions-btn').click(function() {
        const leaveRequestId = $(this).attr('data-lr-id');
        $('#pastActionsModal').modal('show');
        loadPastActions(leaveRequestId);
    });

    function loadPastActions(leaveRequestId) {
        $.ajax({
            type: 'GET',
            url: '/api/leave-request-status/' + leaveRequestId,
            dataType: 'json',
            success: function(pastActions) {
                const modalTable = $('#pastActionsTable').DataTable();
                modalTable.clear().draw();

                pastActions.forEach(function(action) {
                    modalTable.row.add([
                        action.date,
                        action.pic.name,
                        action.statusAction.name
                    ]).draw(false);
                });
            },
            error: function() {
                console.log('Error fetching past actions.');
            }
        });
    }
});