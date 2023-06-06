
//对后端返回的个人信息数据的处理
export function handlerData(obj) {

    const res = new Object();
    res.age = obj.age || "保密";
    res.createTime = obj.createTime;
    res.hobbys = obj.hobbys === null ? null : strToarr(obj.hobbys)
    res.phone = obj.phone;
    res.sex = obj.sex;
    res.username = obj.username;

    return res;
}




//规则以空格分割,用于对用户爱好的转换
export function strToarr(str) {

    const arr = str.split(" ");
    const res = [];

    for (let i = 0; i < arr.length; i++) {

        const p = new Object();
        p.name = arr[i];
        res.push(p)
    }

    return res;
}
//规则以空格分割,用于对用户爱好的转换
export function arrtoStr(arr) {
    const str = ""
    for (let index = 0; index < arr.length; index++) {
        if (index == 0) {
            str += arr[i].name;
            continue;
        }
        str += " ";
        str += arr[i].name;
    }
}