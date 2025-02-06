/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/29
 */
export class WlTimeUtil {
  /**
   * Format time from seconds to HH:mm:ss or mm:ss format
   * @param second - Time in seconds
   * @returns Formatted time string
   */
  public static secondToTimeFormat(second: number): string {
    if (second < 0) {
      return "00:00"
    }

    let time: string = ""; // Clear the existing time string

    const hours: number = Math.floor(second / 3600);
    const minutes: number = Math.floor((second % 3600) / 60);
    const seconds: number = Math.floor(second % 60);

    if (hours > 0) {
      time += (hours >= 10 ? hours : "0" + hours) + ":";
    }

    time += (minutes >= 10 ? minutes : "0" + minutes) + ":";
    time += (seconds >= 10 ? seconds : "0" + seconds);

    return time;
  }

  /**
   * Format time from seconds to HH:mm:ss format
   * @param second - Time in seconds
   * @returns Formatted time string
   */
  public static secondToTimeFormat3(second: number): string {
    if (second < 0) {
      return "00:00:00"
    }

    let time: string = "";

    const hours: number = Math.floor(second / 3600);
    const minutes: number = Math.floor((second % 3600) / 60);
    const seconds: number = Math.floor(second % 60);

    time += (hours >= 10 ? hours : "0" + hours) + ":";
    time += (minutes >= 10 ? minutes : "0" + minutes) + ":";
    time += (seconds >= 10 ? seconds : "0" + seconds);

    return time;
  }
}
