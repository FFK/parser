package ffk.league.git;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.util.CollectionUtils;

public class GitCommiter {

	private String gitPassword;
	private String gitUsername;

	public GitCommiter(String gitUsername, String gitPassword) {
		this.gitPassword = gitPassword;
		this.gitUsername = gitUsername;
	}

	public boolean hasChanges() {
		try (Git git = Git.open(new File(System.getProperty("user.dir") + "/output/.git"))) {
			DiffCommand diffCommand = git.diff();
			List<DiffEntry> diffList = diffCommand.call();
			return !CollectionUtils.isEmpty(diffList);
		} catch (IOException | GitAPIException e) {
			throw new IllegalStateException(e);
		}
	}

	public void push() {
		try (Git git = Git.open(new File(System.getProperty("user.dir") + "/output/.git"))) {
			git.commit().setMessage("test").setAuthor("Mincer", "faker.faker.klama@gmail.com").setAll(true)
					.setAllowEmpty(false).call();
			git.push().setRemote("origin")
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitPassword)).call();
		} catch (IOException | GitAPIException e) {
			throw new IllegalStateException(e);
		}
	}
}
