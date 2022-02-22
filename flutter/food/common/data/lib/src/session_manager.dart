import 'package:api/api.dart';
import 'session_preference.dart';

class SessionManager {
  SessionManager() : _sessionPreference = SessionPreference();

  final SessionPreference _sessionPreference;

  Session? _session;

  Session? get session => _session;

  Future saveSession(Session session) async {
    _session = _session;
    await _sessionPreference.saveAccessToken(session.accessToken);
  }

  Future<bool> isLoggedIn() async {
    if (_session != null) return true;
    return (await _sessionPreference.getAccessToken()).isNotEmpty;
  }
}
