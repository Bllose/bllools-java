import java.io.IOException;
import java.net.URLDecoder;

import com.bllools.service.SilentService;
import com.bllools.util.CommonUtil;
import com.bllools.util.TraversingFolders;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = URLDecoder.decode(path.substring(0, path.lastIndexOf('/')), "UTF-8");
        CommonUtil.log("文件所在路径为" + path);

        SilentService silentService = new SilentService();
        TraversingFolders handler = silentService::dealWithFile;

        handler.traversingFolders(path);
        silentService.process(path);
    }
}
