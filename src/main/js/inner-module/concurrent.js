// noinspection JSUnresolvedVariable,JSUnresolvedFunction

import {id} from ":plugin-id"

const CommonJSPluginClass = Java.type("cn.nukkit.plugin.CommonJSPlugin");
const JSConcurrentManagerClass = Java.type("cn.nukkit.plugin.JSConcurrentManager");

const jsPlugin = CommonJSPluginClass.jsPluginIdMap.get(id);
const concurrentManager = new JSConcurrentManagerClass(jsPlugin);

/**
 * 将对象包装为原子对象
 * @param obj
 */
export function atomic(obj) {
    return concurrentManager.warpSafe(obj);
}

export class Worker {
    /**
     * 创建一个Worker
     * @param sourcePath {string} worker源代码路径
     * @param startImmediately {boolean} 是否立即启动worker执行，默认true
     */
    constructor(sourcePath, startImmediately = true) {
        this.jsWorker = concurrentManager.createWorker(sourcePath);
        this.jsWorker.init();
        Object.defineProperty(this, 'onmessage', {
            get: function () {
                return this.jsWorker.getSourceReceiveCallback();
            },
            set: function (callback) {
                return this.jsWorker.setSourceReceiveCallback(callback);
            }
        });
        if (startImmediately) {
            this.jsWorker.start();
        }
    }

    /**
     * 终结（强制停止）此Worker
     */
    terminate() {
        this.jsWorker.close();
    }
}