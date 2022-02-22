import 'package:shared_preferences/shared_preferences.dart';

class SessionPreference {
  static const _keyAccessToken = "access_token";

  Future<String> getAccessToken() async {
    return (await SharedPreferences.getInstance()).getString(_keyAccessToken) ?? "";
  }

  Future saveAccessToken(String token) async {
    return (await SharedPreferences.getInstance()).setString(_keyAccessToken, token);
  }
}
