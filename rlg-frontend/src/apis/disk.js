import web from "@utils/Http";

export function upload(parentId, file)
{
    let data = new FormData();
    data.set('parentId', parentId);
    data.set('file', file);

    web(
        {
            method: 'POST',
            url: '/file/upload',
            data: data
        }
    )
}

export function mkdir(parentId, name)
{
    let data = new FormData();
    data.set('parentId', parentId);
    data.set('name', name);

    web(
        {
            method: 'POST',
            url: '/file/mkdir',
            data: data
        }
    )
}