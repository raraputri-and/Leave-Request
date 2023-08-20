// var counter = 1
$(document).ready(function () {
  table = $('#table-tracking').DataTable({
    destroy: true,
    ajax: {
      url: '/api/leave-request/user-tracking', // replace with your endpoint
      dataSrc: ''
    },
    columns: [
      {
        data: null,
        title: 'No'
        // render: function () {
        //   return counter++;
        // }
      },
      {
        data: 'id',
        visible: false
      },
      {
        data: 'leaveType.name',
        title: 'Leave Type'
      },
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
          console.log(data)
          if (data.attachment === '') {
            return `<div class="d-flex gap-2">
                        <button class="btn btn-info px-3 py-2" type="button" data-bs-toggle="modal" 
                        data-bs-target="#pastActionsModal" onclick="showPast(${data.id})"><span
                        class="bi bi-pencil-square">Show
                        </button>
                        </div>`
          }
          return `
          <div class = "d-flex justify-content-evenly "> 
            <div class= "d-flex gap-2">
                        <button class="btn btn-info px-3 py-2" type="button" data-bs-toggle="modal" 
                        data-bs-target="#pastActionsModal" onclick="showPast(${data.id})"><span
                        class="bi bi-pencil-square">Show
                        </button>
                        <button class="btn btn-primary px-3 py-2" type="button"
                            data-bs-toggle="modal" data-bs-target="#attachment" onclick="openAttachmentself(${data.id})">
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

  // Append category filter dropdown to DataTable filter
  $("#table-tracking_filter.dataTables_filter").append($("#categoryFilter"));

  // Get the column index for the Leave Type column
  var leaveTypeIndex = 3; // Assuming Leave Type is the 3rd column (0-indexed)

  // Use DataTables API to filter rows by Leave Type column
  $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
    var selectedItem = $('#categoryFilter').val();
    var leaveType = data[leaveTypeIndex];
    if (selectedItem === "" || leaveType.includes(selectedItem)) {
      return true;
    }
    return false;
  });
  // Set change event for the Category Filter dropdown to redraw the DataTable
  $("#categoryFilter").change(function (e) {
    table.draw();
  });

  table.draw();
});


function showReason(id) {
  $("#pastActionsModal").modal("hide");

  $.ajax({
    method: "GET",
    url: `/api/leave-request/${id}`,
    dataType: "JSON",
    success: function (result) {
      $("#reasonText").text(result.reason);
      $("#reasonActionsModal").modal("show");

    }
  })
}

function showPast(id) {
  var clickedId = id;
  $.ajax({
    method: "GET",
    url: `/api/leave-request-status/get-leave-request/${id}`,
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    success: function (data) {
      console.log("Received data:", data); // Add this line after the 'success' function starts
      if (data.length > 0) {

        let listItemHTML = $("#list");
        $('#list').empty()
        $.each(data, function (index, result) {

          var pictIcon;
          switch (result.statusAction.id) {
            case 1:
              pictIcon = "fas fa-check";
              break;
            case 2:
              pictIcon = "fas fa-times";
              break;
            case 3:
              pictIcon = "fa-regular fa-clock";
              break;
            default:
              pictIcon = "fas fa-question-circle"; // Default icon
          }

          const formattedDate = new Date(result.date).toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
          }).split('/').join('-');

          const noteContent = result.note ? result.note : "Created By Employee";

          listItemHTML.append('<div class="d-flex gap-2 activity-list activity-border">' +
            '<div class="activity-icon avatar-md">' +
            '<i class="' + pictIcon + '"></i>' +
            '</div>' + '<div className="media">' + '<div class="me-3">' +
            '<p class="text-muted font-size-14 mb-0">' + result.statusAction.name + ' | By : ' + result.pic.name + '</p>' +
            '<p class="text-muted font-size-14 mb-0">' + formattedDate + '</p>' +
            '<p class="text-muted font-size-14 mb-0">' + "Note : " + noteContent + '</p>' +
            '<p class="text-muted font-size-14 mb-0">' + '</p>' +
            '</div>' + '<div class="media-body">' + '<div class="text-end d-none d-md-block">' +
            '</div>' + '</div>' + '</div>' + '</div>' + '<hr>');

        });

        $('#pastActionsModal .btn-warning').attr('onclick', `showReason(${clickedId})`);
        $("#pastActionsModal").modal("show");
      }
    }
  });
}

function openAttachmentself(id){
  let url = `/api/leave-request/attachment/${id}`
  $('#image').attr('src', url)
}

$("#reasonActionsModal").on("hidden.bs.modal", function () {
  // Show the pastActionsModal again
  $("#pastActionsModal").modal("show");
});
