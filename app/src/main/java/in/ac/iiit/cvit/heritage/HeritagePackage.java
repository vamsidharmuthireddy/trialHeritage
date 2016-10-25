package in.ac.iiit.cvit.heritage;

public class HeritagePackage {

    private String _imagePath;
    private String _siteName;
    private String _packageUrl;

    public HeritagePackage(String imagePath, String siteName, String packageUrl) {
        _imagePath = imagePath;
        _siteName = siteName;
        _packageUrl = packageUrl;
    }

    String get_imagePath() {
        return _imagePath;
    }

    String get_siteName() {
        return _siteName;
    }

    String get_packageUrl() {
        return _packageUrl;
    }
}
