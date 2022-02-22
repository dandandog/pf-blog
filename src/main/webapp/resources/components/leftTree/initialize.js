function initTree(clientId) {
    var mutationObserver = new MutationObserver(function (mutations) {
        mutations.forEach(function (mutation) {
            if ($('.ui-treetable-selectable-node').hasClass('ui-state-hover')) {
                $('.ui-treetable-selectable-node.ui-state-hover').find('.opts').show()
                $('.ui-treetable-selectable-node.ui-state-hover').find('.sub-title').hide()
            } else {
                $('.ui-treetable-selectable-node').find('.opts').hide()
                $('.ui-treetable-selectable-node').find('.sub-title').show()
            }
        });
    });
    /**Element**/
    mutationObserver.observe(clientId, {
        attributes: true,
        characterData: true,
        childList: true,
        subtree: true,
        attributeOldValue: true,
        characterDataOldValue: true
    });
}