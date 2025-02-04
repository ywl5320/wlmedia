/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/27
 */
import { fileIo } from "@kit.CoreFileKit";

export class FileUtil {
  public static resourcesFile2SandboxFile(rawFilePath: string, sandBoxPath: string) {
    let uint8Array: Uint8Array = getContext().resourceManager.getRawFileContentSync(rawFilePath);
    let file: fileIo.File = fileIo.openSync(sandBoxPath, fileIo.OpenMode.READ_WRITE | fileIo.OpenMode.CREATE);
    fileIo.writeSync(file.fd, uint8Array.buffer as ArrayBuffer);
    fileIo.closeSync(file);
  }
}