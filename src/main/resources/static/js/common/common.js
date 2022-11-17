function newCkeditor(target, isReadOnly) {
    return ClassicEditor.create(target, {
        plugin: ['ListStyle', 'Markdown', 'MediaEmbed', 'MediaEmbedToolbar'],
        toolbar: {
            items: [
                'heading',
                '|',
                'underline',
                'bold',
                'italic',
                'link',
                'bulletedList',
                'numberedList',
                'todoList',
                '|',
                'outdent',
                'indent',
                '|',
                'imageInsert',
                'imageUpload',
                'blockQuote',
                'insertTable',
                'mediaEmbed',
                'markdown',
                'undo',
                'redo',
                '|',
                'highlight',
                'fontFamily',
                'fontColor',
                'fontBackgroundColor',
                'fontSize',
                '|',
                'htmlEmbed',
                'specialCharacters'
            ]
        },
        language: 'en',
        image: {
            toolbar: [
                'imageTextAlternative',
                'imageStyle:full',
                'imageStyle:side'
            ]
        },
        table: {
            contentToolbar: [
                'tableColumn',
                'tableRow',
                'mergeTableCells',
                'tableCellProperties',
                'tableProperties'
            ]
        },
    }).then(editor => {
        window.editor = editor;
        editor.isReadOnly = isReadOnly; //읽기 모드
        //editor.style.visibility = 'block';
    }).catch(error => {
        console.error(error);
    });
}