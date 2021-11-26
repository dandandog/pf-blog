function start() {
    PF('statusDialog').show();
}

function stop() {
    PF('statusDialog').hide();
}

function tags(i) {
    switch (i) {
        case '0':
            return 'ui-button-secondary';
        case '1':
            return 'ui-button-info';
        case '2':
            return 'ui-button-help';
        case  '3':
            return 'ui-button-warning';
    }
}
